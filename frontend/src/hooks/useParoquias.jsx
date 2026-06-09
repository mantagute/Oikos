import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import paroquiaService from '../services/paroquiaService';
import { useToast } from '../components/common/useToast';

export function useParoquias() {
    const navigate = useNavigate();
    const [atualizador, setAtualizador] = useState(0);
    const [paroquias, setParoquias] = useState([]);
    const [novoNome, setNovoNome] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [senhasIn, setSenhasIn] = useState({});
    const [erro, setErro] = useState(null);
    const { toast, mostrarToast, fecharToast } = useToast();

    useEffect(() => {
        const carregarParoquias = async () => {
            try {
                const dados = await paroquiaService.getListaParoquias();
                setParoquias(dados);
            } catch (error) {
                console.error('Erro ao buscar paroquias:', error);
                setErro('Não foi possível carregar as paróquias. Verifique sua conexão e tente novamente.');
            }
        };
        carregarParoquias();
    }, [atualizador]);

    const lidarCriarParoquia = async () => {
        if (!novoNome.trim()) return mostrarToast('Digite um nome para a nova paróquia.', 'erro');
        if (!novaSenha.trim()) return mostrarToast('Digite uma senha para a nova paróquia.', 'erro');

        try {
            await paroquiaService.criarParoquia(novoNome, novaSenha);
            setNovoNome('');
            setNovaSenha('');
            setAtualizador((prev) => prev + 1);
            mostrarToast('Paróquia criada com sucesso!', 'sucesso');
        } 
        catch (error) {
            console.error('Erro ao criar paroquia:', error);
        }
    };

    const lidarEntrarParoquia = async (paroquiaId) => {
        const senha = senhasIn[paroquiaId];
        if (!senha || !senha.trim()) return mostrarToast('Digite a senha da paróquia.', 'erro');

        try {
            const resposta = await paroquiaService.autenticarSenhaParoquia(paroquiaId, senha);

            if (resposta === true) {
                navigate(`/paroquia/${paroquiaId}`);
            } else {
                mostrarToast('Senha incorreta. Tente novamente.', 'erro');
            }

            setSenhasIn((prev) => ({ ...prev, [paroquiaId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            mostrarToast('Erro ao verificar senha. Tente novamente.', 'erro');
        }
    };

    const lidarDeletarParoquia = async (paroquiaId) => {
        const senha = senhasIn[paroquiaId];
        if (!senha || !senha.trim()) return mostrarToast('Digite a senha da paróquia para excluí-la.', 'erro');

        try {
            const resposta = await paroquiaService.autenticarSenhaParoquia(paroquiaId, senha);

            if (resposta === true) {
                await paroquiaService.excluirParoquia(paroquiaId, senha);
                mostrarToast('Paróquia excluída.', 'sucesso');
            } else {
                mostrarToast('Senha incorreta. Tente novamente.', 'erro');
            }

            setAtualizador((prev) => prev + 1);
            setSenhasIn((prev) => ({ ...prev, [paroquiaId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao excluir paróquia:', error);
            mostrarToast('Erro ao verificar senha. Tente novamente.', 'erro');
        }
    };

    return {
        paroquias,
        erro,
        novoNome,
        setNovoNome,
        novaSenha,
        setNovaSenha,
        senhasIn,
        setSenhasIn,
        toast,
        fecharToast,
        lidarCriarParoquia,
        lidarEntrarParoquia,
        lidarDeletarParoquia,
    };
}