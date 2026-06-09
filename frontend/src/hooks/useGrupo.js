import { useState, useEffect } from 'react';
import grupoService from '../services/grupoService';
import pessoaService from '../services/pessoaService';
import eventoService from '../services/eventoService';
import notificacaoService from '../services/notificacaoService';
import  useToast  from './useToast';

export function useGrupo(id) {
  const [grupo, setGrupo] = useState(null);
  const [pessoas, setPessoas] = useState([]);
  const [eventos, setEventos] = useState([]);
  const [notificacoes, setNotificacoes] = useState([]);
  const [pessoaSelecionada, setPessoa] = useState(null);
  const [eventoSelecionado, setEvento] = useState(null);
  const [novaMeta, setNovaMeta] = useState(null);
  const [erro, setErro] = useState(null);
  const { toast, mostrarToast, fecharToast } = useToast();

  useEffect(() => {
    const carregarDados = async () => {
      try {
        const [dadosGrupo, dadosPessoas, dadosEventos, dadosNotificacoes] = await Promise.all([
          grupoService.getGrupoPorId(id),
          pessoaService.getListaPessoas(id),
          eventoService.getListaEventos(id),
          notificacaoService.getListaNotificacoes(id)
        ]);
        setGrupo(dadosGrupo);
        setPessoas(dadosPessoas);
        setEventos(dadosEventos);
        setNotificacoes(dadosNotificacoes);
      } catch (error) {
        console.error('Erro ao carregar grupo:', error);
        setErro('Não foi possível carregar os dados do grupo. Verifique sua conexão e tente novamente.');
      }
    };

    carregarDados();
  }, [id]);

  const lidarPontuar = async () => {
    if (!pessoaSelecionada || !eventoSelecionado) {
      return mostrarToast('Selecione uma pessoa e um evento para pontuar.', 'erro');
    }
    try {
      await grupoService.registrarAtividade(id, pessoaSelecionada, eventoSelecionado);
      const dadosGrupo = await grupoService.getGrupoPorId(id);
      setGrupo(dadosGrupo);
      mostrarToast('Atividade registrada! Seu grupo agradece.', 'sucesso');
    } 
    catch (error) {
      console.error('Erro ao registrar atividade:', error);
      mostrarToast('Erro ao registrar atividade. Tente novamente.', 'erro');
    }
  }

  const onAtualizarPessoas = async () => {
    try {
      const dadosPessoas = await pessoaService.getListaPessoas(id);
      setPessoas(dadosPessoas);
    } 
    catch (error) {
      console.error('Erro ao atualizar pessoas:', error);
    }
  }

  const onAtualizarEventos = async () => {
    try {
      const dadosEventos = await eventoService.getListaEventos(id);
      setEventos(dadosEventos);
    } catch (error) {
      console.error("Erro ao atualizar eventos:", error);
    }
  }

  const onAtualizarNotificacoes = async () => {
    try {
      const dadosNotificacoes = await notificacaoService.getListaNotificacoes(id);
      setNotificacoes(dadosNotificacoes);
    } catch (error) {
      console.error("Erro ao atualizar notificacoes:", error);
    }
  };

  const lidarRedefinirMeta = async (meta) => {
    try {
      if (!meta) return mostrarToast('Insira a nova meta.', 'erro');
      await grupoService.redefinirMetaGrupo(id, meta);
      const dadosGrupo = await grupoService.getGrupoPorId(id);
      setGrupo(dadosGrupo);
      setNovaMeta('');
      mostrarToast('Meta atualizada com sucesso!', 'sucesso');
    } catch (error) {
      console.error('Erro ao redefinir a meta:', error);
    }
  }

  return {
    grupo,
    erro,
    pessoas,
    eventos,
    notificacoes,
    pessoaSelecionada,
    setPessoa,
    eventoSelecionado,
    setEvento,
    novaMeta,
    setNovaMeta,
    toast,
    fecharToast,
    lidarPontuar,
    onAtualizarPessoas,
    onAtualizarEventos,
    onAtualizarNotificacoes,
    lidarRedefinirMeta
  };
}
