import api from './api';

const eventoService = {
    getListaEventos: async (grupoId) => {
        const response = await api.get(`/grupos/${grupoId}/eventos`);
        return response.data
    },

    getEventoPorId: async (grupoId, eventoId) => {
        const response = await api.get(`grupos/${grupoId}/eventos/${eventoId}`);
        return response.data;
    },

    criarEvento: async (grupoId, nome, pontos) => {
        const response = await api.post(`grupos/${grupoId}/eventos`, {nome, pontos});
        return response.data;
    },

    excluirEvento: async (grupoId, eventoId) => {
        const response = await api.delete(`grupos/${grupoId}/eventos/${eventoId}`);
        return response.data;
    }
}

export default eventoService;