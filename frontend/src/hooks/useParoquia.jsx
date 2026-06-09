import { useState, useEffect } from 'react';
import paroquiaService from '../services/paroquiaService';
import  useToast  from './useToast';

export function useParoquia(idParoquia) {
    const [paroquia, setParoquia] = useState(null);
    const [gruposVinculados, setGruposVinculados] = useState([]);
    const [mensagemNotificacao, setMensagemNotificacao] = useState('');

    // Array com IDs dos grupos que receberão a notificação específica. 
    // Se estiver vazio, a notificação vai para todos.
    const [gruposSelecionados, setGruposSelecionados] = useState([]);
    const [erro, setErro] = useState(null);
    const { toast, mostrarToast, fecharToast } = useToast();

    useEffect(() => {
        const carregarDados = async() => {
            try {
                const [dadosParoquia, dadosGruposVinculados] = await Promise.all([
                    paroquiaService.getParoquiaPorId(idParoquia),
                    paroquiaService.getGruposVinculados(idParoquia)
                ]);
                setParoquia(dadosParoquia);
                setGruposVinculados(dadosGruposVinculados);
            } catch (error) {
                console.error('Erro ao carregar paroquia:', error);
                setErro('Não foi possível carregar os dados da paróquia. Verifique sua conexão e tente novamente.');
            }
        }
        carregarDados();
    }, [idParoquia]);

    const lidarEnviarNotificacao = async () => {
        if (!mensagemNotificacao.trim()) {
            return mostrarToast('Digite uma mensagem para a notificação.', 'erro');
        }

        try {
            const destinatarios = gruposSelecionados.length > 0 ? gruposSelecionados : null;

            await paroquiaService.enviarNotificacoes(idParoquia, mensagemNotificacao, destinatarios);
            setMensagemNotificacao('');
            setGruposSelecionados([]);
            mostrarToast('Notificação enviada com sucesso!', 'sucesso');
        } catch (error) {
            console.error('Erro ao enviar notificação:', error);
            mostrarToast('Erro ao enviar notificação. Tente novamente.', 'erro');
        }
    };

    const alternarSelecaoGrupo = (grupoId) => {
        setGruposSelecionados((selecionadosAtuais) => {
            const jaEstaSelecionado = selecionadosAtuais.includes(grupoId);
            if (jaEstaSelecionado) {
                return selecionadosAtuais.filter((id) => id !== grupoId);
            } 
            else {
                return [...selecionadosAtuais, grupoId];
            }
        });
    };

    return {
        paroquia,
        erro,
        gruposVinculados,
        mensagemNotificacao,
        setMensagemNotificacao,
        gruposSelecionados,
        alternarSelecaoGrupo,
        toast,
        fecharToast,
        lidarEnviarNotificacao,
    };
}