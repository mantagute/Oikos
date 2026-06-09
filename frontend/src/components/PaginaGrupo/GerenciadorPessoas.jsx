import {useState} from 'react';
import pessoaService  from '../../services/pessoaService';
import { InputOikos, BotaoOikos } from '../common';
import './GerenciadorPessoas.css';

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
        <section className='gerenciador-pessoas'>
            <header>
                <h2>Pessoas</h2>
            </header>
            <ul className='lista-pessoa'>
                {pessoas.map((pessoa) => (
                    <li className='pessoa-item' key={pessoa.id}>
                        <span className="pessoa-nome">{pessoa.nome}</span>
                        <BotaoOikos variante="perigo" onClick={() => lidarExcluirPessoa(pessoa.id)}>Excluir</BotaoOikos>
                    </li>
                ))}
            </ul>
            <div className='caixa-criar-pessoa'>
                <InputOikos 
                    placeholder="Nome da nova pessoa"
                    value={novoNome}
                    onChange={(e) => setNovoNome(e.target.value)}
                />
                <BotaoOikos variante="primario" onClick={lidarCriarPessoa}>Adicionar Pessoa</BotaoOikos> 
            </div>
        </section>
    );
}

export default GerenciadorPessoas;