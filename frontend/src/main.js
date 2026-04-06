import './style.css';

const state = {
  apiBase: '',
  empresas: [],
  funcionarias: [],
  empresaId: '',
  somenteAtivas: false,
};

function apiUrl(path) {
  const base = state.apiBase.replace(/\/$/, '');
  return base ? `${base}${path}` : path;
}

async function request(path, options = {}) {
  const res = await fetch(apiUrl(path), {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  });
  const text = await res.text();
  let data = null;
  if (text) {
    try {
      data = JSON.parse(text);
    } catch {
      data = text;
    }
  }
  if (!res.ok) {
    const msg =
      data && typeof data === 'object' && data.erro != null
        ? String(data.erro)
        : `HTTP ${res.status}`;
    throw new Error(msg);
  }
  return data;
}

function toast(message, type = 'error') {
  const el = document.createElement('div');
  el.className = `toast toast-${type === 'ok' ? 'ok' : 'error'}`;
  el.textContent = message;
  document.body.appendChild(el);
  setTimeout(() => el.remove(), 4500);
}

async function carregarEmpresas() {
  const list = await request('/empresas');
  state.empresas = Array.isArray(list) ? list : [];
  render();
}

async function carregarFuncionarias() {
  if (!state.empresaId) {
    state.funcionarias = [];
    render();
    return;
  }
  const id = state.empresaId;
  const path = state.somenteAtivas
    ? `/funcionarias/empresa/${id}/ativas`
    : `/funcionarias/empresa/${id}`;
  const list = await request(path);
  state.funcionarias = Array.isArray(list) ? list : [];
  render();
}

function render() {
  const app = document.getElementById('app');
  app.innerHTML = `
    <header>
      <h1>Funcionárias</h1>
      <p class="subtitle">Gerencie o time por empresa — consome <code>FuncionariaController</code> e <code>EmpresaController</code>.</p>
    </header>

    <section class="panel">
      <h2>Conexão</h2>
      <div class="row">
        <div class="field" style="flex: 1; min-width: 280px;">
          <label for="apiBase">URL base da API (vazio = proxy Vite → localhost:8080)</label>
          <input type="url" id="apiBase" placeholder="http://localhost:8080" value="${escapeAttr(state.apiBase)}" />
        </div>
        <button type="button" class="btn-ghost" id="btnSalvarBase">Aplicar URL</button>
      </div>
      <p class="hint">Com <code>npm run dev</code>, deixe em branco. Para build estático ou outra porta, informe a URL do Spring Boot.</p>
    </section>

    <section class="panel">
      <h2>Filtro</h2>
      <div class="row">
        <div class="field">
          <label for="empresaSelect">Empresa</label>
          <select id="empresaSelect">
            <option value="">Selecione…</option>
            ${state.empresas
              .map(
                (e) =>
                  `<option value="${e.id}" ${String(e.id) === String(state.empresaId) ? 'selected' : ''}>${escapeHtml(e.nome)}</option>`
              )
              .join('')}
          </select>
        </div>
        <div class="field checkbox-field">
          <input type="checkbox" id="somenteAtivas" ${state.somenteAtivas ? 'checked' : ''} />
          <label for="somenteAtivas">Somente ativas</label>
        </div>
        <button type="button" class="btn-primary" id="btnAtualizarLista">Atualizar lista</button>
        <button type="button" class="btn-ghost" id="btnRecarregarEmpresas">Recarregar empresas</button>
      </div>
    </section>

    <section class="panel">
      <h2>Nova funcionária</h2>
      <p class="hint" style="margin: 0 0 0.5rem;">Envia JSON com <code>empresa: { id }</code> e <code>usuario: { email, senha }</code> (papel definido no backend).</p>
      <div class="grid-form">
        <div class="field">
          <label for="nfNome">Nome</label>
          <input type="text" id="nfNome" required autocomplete="name" />
        </div>
        <div class="field">
          <label for="nfEspecialidades">Especialidades</label>
          <input type="text" id="nfEspecialidades" required placeholder="Ex.: Manicure, cabelo" />
        </div>
        <div class="field">
          <label for="nfFoto">Foto (URL, opcional)</label>
          <input type="url" id="nfFoto" placeholder="https://..." />
        </div>
        <div class="field">
          <label for="nfEmail">E-mail (login)</label>
          <input type="email" id="nfEmail" required autocomplete="username" />
        </div>
        <div class="field">
          <label for="nfSenha">Senha</label>
          <input type="password" id="nfSenha" required autocomplete="new-password" />
        </div>
      </div>
      <div class="form-actions">
        <button type="button" class="btn-primary" id="btnCadastrar">Cadastrar</button>
      </div>
    </section>

    <section class="panel">
      <h2>Lista</h2>
      ${
        !state.empresaId
          ? '<p class="empty">Escolha uma empresa para listar as funcionárias.</p>'
          : state.funcionarias.length === 0
            ? '<p class="empty">Nenhuma funcionária neste filtro.</p>'
            : `<div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Especialidades</th>
              <th>Status</th>
              <th>E-mail</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            ${state.funcionarias
              .map(
                (f) => `
              <tr data-id="${f.id}">
                <td>${f.id}</td>
                <td>${escapeHtml(f.nome)}</td>
                <td>${escapeHtml(f.especialidades || '')}</td>
                <td><span class="badge ${f.ativo ? 'badge-on' : 'badge-off'}">${f.ativo ? 'Ativa' : 'Inativa'}</span></td>
                <td>${escapeHtml(f.usuario?.email || '')}</td>
                <td class="actions">
                  <button type="button" class="btn-ghost btn-sm btn-editar">Editar</button>
                  <button type="button" class="btn-ghost btn-sm btn-desativar" ${!f.ativo ? 'disabled' : ''}>Desativar</button>
                  <button type="button" class="btn-ghost btn-sm btn-reativar" ${f.ativo ? 'disabled' : ''}>Reativar</button>
                  <button type="button" class="btn-danger btn-sm btn-excluir">Excluir</button>
                </td>
              </tr>
            `
              )
              .join('')}
          </tbody>
        </table>
      </div>`
      }
    </section>
  `;

  bind();
}

function escapeHtml(s) {
  if (s == null) return '';
  return String(s)
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;');
}

function escapeAttr(s) {
  return escapeHtml(s).replace(/'/g, '&#39;');
}

function bind() {
  document.getElementById('btnSalvarBase')?.addEventListener('click', () => {
    state.apiBase = document.getElementById('apiBase').value.trim();
    carregarEmpresas().catch((e) => toast(e.message));
  });

  document.getElementById('empresaSelect')?.addEventListener('change', (e) => {
    state.empresaId = e.target.value;
    carregarFuncionarias().catch((err) => toast(err.message));
  });

  document.getElementById('somenteAtivas')?.addEventListener('change', (e) => {
    state.somenteAtivas = e.target.checked;
    carregarFuncionarias().catch((err) => toast(err.message));
  });

  document.getElementById('btnAtualizarLista')?.addEventListener('click', () => {
    carregarFuncionarias().catch((e) => toast(e.message));
  });

  document.getElementById('btnRecarregarEmpresas')?.addEventListener('click', () => {
    carregarEmpresas().catch((e) => toast(e.message));
  });

  document.getElementById('btnCadastrar')?.addEventListener('click', async () => {
    if (!state.empresaId) {
      toast('Selecione uma empresa antes de cadastrar.');
      return;
    }
    const nome = document.getElementById('nfNome').value.trim();
    const especialidades = document.getElementById('nfEspecialidades').value.trim();
    const foto = document.getElementById('nfFoto').value.trim();
    const email = document.getElementById('nfEmail').value.trim();
    const senha = document.getElementById('nfSenha').value;
    if (!nome || !especialidades || !email || !senha) {
      toast('Preencha nome, especialidades, e-mail e senha.');
      return;
    }
    const body = {
      nome,
      especialidades,
      empresa: { id: Number(state.empresaId) },
      usuario: { email, senha },
    };
    if (foto) body.foto = foto;
    try {
      await request('/funcionarias', { method: 'POST', body: JSON.stringify(body) });
      toast('Funcionária cadastrada.', 'ok');
      document.getElementById('nfNome').value = '';
      document.getElementById('nfEspecialidades').value = '';
      document.getElementById('nfFoto').value = '';
      document.getElementById('nfEmail').value = '';
      document.getElementById('nfSenha').value = '';
      await carregarFuncionarias();
    } catch (e) {
      toast(e.message);
    }
  });

  document.querySelectorAll('.btn-editar').forEach((btn) => {
    btn.addEventListener('click', () => {
      const tr = btn.closest('tr');
      const id = tr.dataset.id;
      const f = state.funcionarias.find((x) => String(x.id) === id);
      if (!f) return;
      const nome = window.prompt('Nome:', f.nome);
      if (nome === null) return;
      const especialidades = window.prompt('Especialidades:', f.especialidades || '');
      if (especialidades === null) return;
      const foto = window.prompt('URL da foto (vazio para não alterar):', f.foto || '');
      if (foto === null) return;
      const payload = { nome: nome.trim(), especialidades: especialidades.trim() };
      if (foto.trim()) payload.foto = foto.trim();
      request(`/funcionarias/${id}`, { method: 'PUT', body: JSON.stringify(payload) })
        .then(() => {
          toast('Atualizado.', 'ok');
          return carregarFuncionarias();
        })
        .catch((e) => toast(e.message));
    });
  });

  document.querySelectorAll('.btn-desativar').forEach((btn) => {
    btn.addEventListener('click', () => {
      const id = btn.closest('tr').dataset.id;
      request(`/funcionarias/${id}/desativar`, { method: 'PATCH' })
        .then(() => {
          toast('Desativada.', 'ok');
          return carregarFuncionarias();
        })
        .catch((e) => toast(e.message));
    });
  });

  document.querySelectorAll('.btn-reativar').forEach((btn) => {
    btn.addEventListener('click', () => {
      const id = btn.closest('tr').dataset.id;
      request(`/funcionarias/${id}/reativar`, { method: 'PATCH' })
        .then(() => {
          toast('Reativada.', 'ok');
          return carregarFuncionarias();
        })
        .catch((e) => toast(e.message));
    });
  });

  document.querySelectorAll('.btn-excluir').forEach((btn) => {
    btn.addEventListener('click', () => {
      const id = btn.closest('tr').dataset.id;
      if (!window.confirm('Excluir esta funcionária e o usuário vinculado?')) return;
      request(`/funcionarias/${id}`, { method: 'DELETE' })
        .then(() => {
          toast('Excluída.', 'ok');
          return carregarFuncionarias();
        })
        .catch((e) => toast(e.message));
    });
  });
}

render();
carregarEmpresas().catch((e) => toast(e.message));
