import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import paroquiaService from '../services/paroquiaService';

export function useParoquias() {
    const navigate = useNavigate();
    const [atualizador, setAtualizador] = useState(0);
    const [paroquias, setParoquias] = useState([]);
    const [novoNome, setNovoNome] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [senhasIn, setSenhasIn] = useState({});

    useEffect(() => {
        const carregarParoquias = async () => {
        try {
            const dados = await paroquiaService.getListaParoquias();
            setParoquias(dados);
        } catch (error) {
            console.error('Erro ao buscar paroquias:', error);
        }
        };
        carregarParoquias();
    }, [atualizador]);

    const lidarCriarParoquia = async () => {
        if (!novoNome.trim()) return alert('Digite um nome para a paroquia');
        if (novoNome.trim() && !novaSenha.trim())
            return alert('Digite uma senha para a nova paroquia');
        try {
            await paroquiaService.criarParoquia(novoNome, novaSenha);
            setNovoNome('');
            setNovaSenha('');
            setAtualizador((prev) => prev + 1)
        } 
        catch (error) {
            console.error('Erro ao criar paroquia:', error);
        }
    };
    
    const lidarEntrarParoquia = async (paroquiaId) => {
        const senha = senhasIn[paroquiaId];
        if (!senha || !senha.trim()) return alert('Digite a senha da paróquia');
    
        try {
            const resposta = await paroquiaService.autenticarSenhaParoquia(paroquiaId, senha);
        
            if (resposta === true) {
                navigate(`/paroquia/${paroquiaId}`);
            } else {
                alert('Senha incorreta');
            }
        
            setSenhasIn((prev) => ({ ...prev, [paroquiaId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao autenticar senha:', error);
            alert('Erro ao verificar senha.');
        }
    };
    
    const lidarDeletarParoquia = async (paroquiaId) => {
        const senha = senhasIn[paroquiaId];
        if (!senha || !senha.trim())
            return alert('Digite a senha da paróquia para excluí-la');
    
        try {
            const resposta = await paroquiaService.autenticarSenhaParoquia(paroquiaId, senha);
        
            if (resposta === true) {
                await paroquiaService.excluirParoquia(paroquiaId, senha);
            } else {
                alert('Senha incorreta');
            }
        
            setAtualizador((prev) => prev + 1);
            setSenhasIn((prev) => ({ ...prev, [paroquiaId]: '' }));
        } 
        catch (error) {
            console.error('Erro ao excluir paróquia:', error);
            alert('Erro ao verificar senha.');
        }
    };

    return {
        paroquias,
        novoNome,
        setNovoNome,
        novaSenha,
        setNovaSenha,
        senhasIn,
        setSenhasIn,
        lidarCriarParoquia,
        lidarEntrarParoquia,
        lidarDeletarParoquia,
    };
}