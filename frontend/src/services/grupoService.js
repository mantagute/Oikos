import api from './api';

const grupoService = {
  getListaGrupos: async () => {
    const response = await api.get('/grupos');
    return response.data;
  },

  getGrupoPorId: async (id) => {
    const response = await api.get(`/grupos/${id}`);
    return response.data;
  },

  
  criarGrupo: async (nome, senha) => {
    const response = await api.post('/grupos', { nome, senha });
    return response.data;
  },

  // Alinhado com ServicoGrupos.excluirGrupo(UUID, String)
  excluirGrupo: async (id, senha) => {
    const response = await api.delete(`/grupos/${id}`, {
      data: { senha }
    });
    return response.data;
  },

  // Alinhado com ServicoGrupos.pontuar(UUID, UUID, UUID)
  registrarAtividade: async (grupoId, pessoaId, eventoId) => {
    const response = await api.post(`/grupos/${grupoId}/pontuar`, { 
      pessoaId, 
      eventoId 
    });
    return response.data;
  },

  autenticarSenhaGrupo: async (grupoId, senhaIn) => {
    const response = await api.get(`/grupos/${grupoId}/${senhaIn}`);
    return response.data;
  }, 
  
  getClassificacaoGrupo: async (grupoId) => {
    const response = await api.get(`/${grupoId}/classificar`)
    return response.data;
  }
};

export default grupoService;
