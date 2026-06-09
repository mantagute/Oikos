import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import grupoService from '../services/grupoService';
import { useToast } from '../components/common/useToast';

export function useGrupos() {
    const navigate = useNavigate();
    const [atualizador, setAtualizador] = useState(0);
    const [grupos, setGrupos] = useState([]);
    const [novoNome, setNovoNome] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [senhasIn, setSenhasIn] = useState({});
    const [erro, setErro] = useState(null);
    const { toast, mostrarToast, fecharToast } = useToast();

    useEffect(() => {
        const carregarGrupos = async () => {
            try {
                const dados = await grupoService.getListaGrupos();
                setGrupos(dados);
            } catch (error) {
                console.error('Erro ao buscar grupos:', error);
                setErro('Não foi possível carregar os grupos. Verifique sua conexão e tente novamente.');
            }
        };
        carregarGrupos();
    }, [atualizador]);

    const lidarComCriarGrupo = async () => {
        if (!novoNome.trim()) return mostrarToast('Digite um nome para o novo grupo.', 'erro');
        if (!novaSenha.trim()) return mostrarToast('Digite uma senha para o novo grupo.', 'erro');

        try {
            await grupoService.criarGrupo(novoNome, novaSenha);
            setNovoNome('');
            setNovaSenha('');
            setAtualizador((prev) => prev + 1);
            mostrarToast('Grupo criado com sucesso!', 'sucesso');
        } 
        catch (error) {
            console.error('Erro ao criar grupo:', error);
        }
    };

    const lidarEntrarGrupo = async (grupoId) => {
        const senha = senhasIn[grupoId];
        if (!senha || !senha.trim()) return mostrarToast('Digite a senha do grupo.', 'erro');

        try {
            const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

            if (resposta === true) {
                navigate(`/grupo/${grupoId}`);
            } 
            else {
                mostrarToast('Senha incorreta. Tente novamente.', 'erro');
            }

            setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            mostrarToast('Erro ao verificar senha. Tente novamente.', 'erro');
        }
    };

    const lidarDeletarGrupo = async (grupoId) => {
        const senha = senhasIn[grupoId];
        if (!senha || !senha.trim()) return mostrarToast('Digite a senha do grupo para excluí-lo.', 'erro');

        try {
            const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

            if (resposta === true) {
                await grupoService.excluirGrupo(grupoId, senha);
                mostrarToast('Grupo excluído.', 'sucesso');
            } 
            else {
                mostrarToast('Senha incorreta. Tente novamente.', 'erro');
            }

            setAtualizador((prev) => prev + 1);
            setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            mostrarToast('Erro ao verificar senha. Tente novamente.', 'erro');
        }
    };

    return {
        grupos,
        erro,
        novoNome,
        setNovoNome,
        novaSenha,
        setNovaSenha,
        senhasIn,
        setSenhasIn,
        toast,
        fecharToast,
        lidarComCriarGrupo,
        lidarEntrarGrupo,
        lidarDeletarGrupo,
    };
}