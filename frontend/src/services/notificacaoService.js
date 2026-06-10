import api from "./api";

const notificacaoService = {

    getListaNotificacoes: async (grupoId) => {
    const response = await api.get(`/grupos/${grupoId}/notificacoes`);
    return response.data;
    },

    getNotificacaoPorId: async (grupoId, notificacaoId) => {
    const response = await api.get(`/grupos/${grupoId}/notificacoes/${notificacaoId}`)
    return response.data;
    },

    criarNotificacao: async(grupoId, mensagem, idParoquia) => {
    const response = await api.post(`/grupos/${grupoId}/notificacoes`, {mensagem, idParoquia} );
    return response.data;
    },

    marcarComoLida: async(grupoId, notificacaoId) => {
    const response = await api.patch(`/grupos/${grupoId}/notificacoes/${notificacaoId}/lida`);
    return response.data;
    },

    excluirNotificacao: async(grupoId, notificacaoId) => {
    const response = await api.delete(`/grupos/${grupoId}/notificacoes/${notificacaoId}`)
    return response.data;
    },

    aceitarVinculo: async(grupoId, notificacaoId) => {
    const response = await api.post(`/grupos/${grupoId}/notificacoes/${notificacaoId}/aceitar-vinculo`)
    return response.data;
    }

}

export default notificacaoService;