import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import grupoService from '../services/grupoService';

export function useGrupos() {
    const navigate = useNavigate();
    const [atualizador, setAtualizador] = useState(0);
    const [grupos, setGrupos] = useState([]);
    const [novoNome, setNovoNome] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [senhasIn, setSenhasIn] = useState({});
    const [erro, setErro] = useState(null);

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
        if (!novoNome.trim()) return alert('Digite um nome para o novo grupo');
        if (novoNome.trim() && !novaSenha.trim())
            return alert('Digite uma senha para o novo grupo');
        
        try {
            await grupoService.criarGrupo(novoNome, novaSenha);
            setNovoNome('');
            setNovaSenha('');
            setAtualizador((prev) => prev + 1);
        } catch (error) {
            console.error('Erro ao criar grupo:', error);
        }
    };

    const lidarEntrarGrupo = async (grupoId) => {
        const senha = senhasIn[grupoId];
        if (!senha || !senha.trim()) return alert('Digite a senha do grupo');

        try {
            const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

            if (resposta === true) {
                navigate(`/grupo/${grupoId}`);
            } 
            else {
                alert('Senha incorreta');
            }

            setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            alert('Erro ao verificar senha.');
        }
    };

    const lidarDeletarGrupo = async (grupoId) => {
        const senha = senhasIn[grupoId];
        if (!senha || !senha.trim())
            return alert('Digite a senha do grupo para excluí-lo');

        try {
            const resposta = await grupoService.autenticarSenhaGrupo(grupoId, senha);

            if (resposta === true) {
                await grupoService.excluirGrupo(grupoId, senha);
            } else {
                alert('Senha incorreta');
            }

            setAtualizador((prev) => prev + 1);
            setSenhasIn((prev) => ({ ...prev, [grupoId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            alert('Erro ao verificar senha.');
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
        lidarComCriarGrupo,
        lidarEntrarGrupo,
        lidarDeletarGrupo,
    };
}