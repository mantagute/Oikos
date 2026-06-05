import api from './api';

const pessoaService = {
    getListaPessoas: async (grupoId) => {
        const response = await api.get(`/grupos/${grupoId}/pessoas`);
        return response.data
    },

    getPessoaPorId: async (grupoId, pessoaId) => {
        const response = await api.get(`/grupos/${grupoId}/pessoas/${pessoaId}`);
        return response.data;
    },

    criarPessoa: async (grupoId, nome) => {
        const response = await api.post(`grupos/${grupoId}/pessoas`, {nome});
        return response.data;
    },

    excluirPessoa: async (grupoId, pessoaId) => {
        const response = await api.delete(`grupos/${grupoId}/pessoas/${pessoaId}`);
        return response.data;
    }
}

export default pessoaService;