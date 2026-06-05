import {useState} from 'react';
import pessoaService  from '../../services/pessoaService';

function GerenciadorPessoas({grupoId, pessoas, onAtualizarPessoas }) {
    const [novoNome, setNovoNome] = useState('');

    const lidarCriarPessoa = async () => {
        if (!novoNome.trim()) return alert( 'Digite um nome para a nova pessoa');

        try {
            await pessoaService.criarPessoa(grupoId, novoNome);
            setNovoNome('');
            onAtualizarPessoas();
        }
        catch (error) {
            console.error('Erro ao criar pessoa:', error);
        }
    }

    const lidarExcluirPessoa = async (pessoaId) => {
        try {
            await pessoaService.excluirPessoa(grupoId, pessoaId);
            onAtualizarPessoas();
        }
        catch (error) {
            console.error('Erro ao excluir pessoa:', error);
        }
    }

    return (
        <div className=''>
            <h3>Pessoas</h3>
            <ul>
                {pessoas.map((pessoa) => (
                    <li key={pessoa.id}>
                        {pessoa.nome}
                        <button onClick={() => lidarExcluirPessoa(pessoa.id)}>Excluir</button>
                    </li>
                ))}
            </ul>
            <input 
                type="text"
                placeholder="Nome da nova pessoa"
                value={novoNome}
                onChange={(e) => setNovoNome(e.target.value)}
            />
            <button onClick={lidarCriarPessoa}>Adicionar Pessoa</button> 
        </div>
    );
}

export default GerenciadorPessoas;