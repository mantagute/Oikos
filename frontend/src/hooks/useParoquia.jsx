import { useState, useEffect } from 'react';
import paroquiaService from '../services/paroquiaService';
import grupoService from '../services/grupoService';
import  useToast  from './useToast';
import { useConfirm } from './useConfirm';

export function useParoquia(idParoquia) {
    const [paroquia, setParoquia] = useState(null);
    const [gruposVinculados, setGruposVinculados] = useState([]);
    const [todosGrupos, setTodosGrupos] = useState([]);
    const [mensagemNotificacao, setMensagemNotificacao] = useState('');

    // Array com IDs dos grupos que receberão a notificação específica. 
    // Se estiver vazio, a notificação vai para todos.
    const [gruposSelecionados, setGruposSelecionados] = useState([]);
    const [erro, setErro] = useState(null);
    const { toast, mostrarToast, fecharToast } = useToast();
    const { confirmar, ConfirmDialog } = useConfirm();

    useEffect(() => {
        const carregarDados = async() => {
            try {
                const [dadosParoquia, dadosGruposVinculados, dadosTodosGrupos] = await Promise.all([
                    paroquiaService.getParoquiaPorId(idParoquia),
                    paroquiaService.getGruposVinculados(idParoquia),
                    grupoService.getListaGrupos()
                ]);
                setParoquia(dadosParoquia);
                setGruposVinculados(dadosGruposVinculados);
                setTodosGrupos(dadosTodosGrupos);
            } catch (error) {
                console.error('Erro ao carregar paroquia:', error);
                setErro('Não foi possível carregar os dados da paróquia. Verifique sua conexão e tente novamente.');
            }
        }
        carregarDados();
    }, [idParoquia]);

    useEffect(() => {
        const carregarGruposVinculados = async () => {
            try {
                const dados = await paroquiaService.getGruposVinculados(idParoquia);
                setGruposVinculados(dados);
            } catch {
                // silencioso para nao poluir o console
            }
        };

        const intervalo = setInterval(carregarGruposVinculados, 15000);

        const aoFocar = () => carregarGruposVinculados();
        window.addEventListener('focus', aoFocar);

        return () => {
            clearInterval(intervalo);
            window.removeEventListener('focus', aoFocar);
        };
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

    const lidarSolicitarVinculo = async (grupoId) => {
        const confirmado = await confirmar('Enviar solicitação de vínculo para este grupo?');
        if (!confirmado) return;
        try {
            await paroquiaService.solicitarVinculo(idParoquia, grupoId);
            mostrarToast('Solicitação de vínculo enviada para o grupo!', 'sucesso');
        } catch (error) {
            const mensagem = error.response?.data?.mensagem || 'Erro ao solicitar vínculo. Tente novamente.';
            mostrarToast(mensagem, 'erro');
        }
    };

    const lidarDesvincularGrupo = async (grupoId) => {
        const confirmado = await confirmar('Tem certeza que deseja desvincular este grupo?');
        if (!confirmado) return;
        try {
            await paroquiaService.desvincularGrupo(idParoquia, grupoId);
            setGruposVinculados((prev) => prev.filter((g) => g.id !== grupoId));
            mostrarToast('Grupo desvinculado.', 'sucesso');
        } catch (error) {
            mostrarToast('Erro ao desvincular grupo. Tente novamente.', 'erro');
        }
    };

    return {
        paroquia,
        erro,
        gruposVinculados,
        todosGrupos,
        mensagemNotificacao,
        setMensagemNotificacao,
        gruposSelecionados,
        alternarSelecaoGrupo,
        toast,
        fecharToast,
        ConfirmDialog,
        lidarEnviarNotificacao,
        lidarSolicitarVinculo,
        lidarDesvincularGrupo,
    };
}