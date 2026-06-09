import api from './api';

const paroquiaService = {
  getListaParoquias: async () => {
    const response = await api.get('/paroquias');
    return response.data;
  },

  getParoquiaPorId: async (id) => {
    const response = await api.get(`/paroquias/${id}`);
    return response.data;
  },

  criarParoquia: async (nome, senha) => {
    const response = await api.post('/paroquias', { nome, senha });
    return response.data;
  },

  autenticarSenhaParoquia: async (id, senha) => {
    const response = await api.post(`/paroquias/${id}/autenticar`, { senha });
    return response.data;
  },

  excluirParoquia: async (id, senha) => {
    const response = await api.delete(`/paroquias/${id}`, {data: { senha }});
    return response.data;
  },

  getGruposVinculados: async (id) => {
    const response = await api.get(`/paroquias/${id}/grupos`);
    return response.data;
  },

  vincularGrupo: async (id, idGrupo) => {
    const response = await api.post(`/paroquias/${id}/vinculos`, { idGrupo });
    return response.data;
  },

  desvincularGrupo: async (id, grupoId) => {
    const response = await api.delete(`/paroquias/${id}/vinculos/${grupoId}`);
    return response.data;
  },

  // gruposIds: null = envia para todos, lista = envia para selecionados
  enviarNotificacoes: async (id, mensagem, gruposIds = null) => {
    const response = await api.post(`/paroquias/${id}/notificacoes`, { mensagem, gruposIds });
    return response.data;
  }
};

export default paroquiaService;
