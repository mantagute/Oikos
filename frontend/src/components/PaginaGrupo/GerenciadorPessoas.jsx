import { useState } from 'react';
import { BsPeople } from 'react-icons/bs';
import pessoaService from '../../services/pessoaService';
import { InputOikos, BotaoOikos } from '../common';
import { useConfirm } from '../../hooks/useConfirm';
import './GerenciadorPessoas.css';

function GerenciadorPessoas({ grupoId, pessoas, onAtualizarPessoas, onErro }) {
  const { confirmar, ConfirmDialog } = useConfirm();
  const [novoNome, setNovoNome] = useState('');

  const lidarCriarPessoa = async () => {
    if (!novoNome.trim()) return onErro('Digite um nome para a nova pessoa.', 'erro');

    try {
      await pessoaService.criarPessoa(grupoId, novoNome);
      setNovoNome('');
      onAtualizarPessoas();
    } catch (error) {
      console.error('Erro ao criar pessoa:', error);
    }
  };

  const lidarExcluirPessoa = async (pessoaId) => {
    const confirmado = await confirmar('Tem certeza que deseja excluir esta pessoa?');
    if (!confirmado) return;
    try {
      await pessoaService.excluirPessoa(grupoId, pessoaId);
      onAtualizarPessoas();
    } catch (error) {
      console.error('Erro ao excluir pessoa:', error);
    }
  };

  return (
    <>
      {ConfirmDialog}
      <section className="gerenciador-pessoas">
      <header className="secao-header">
        <BsPeople className="secao-icone" />
        <h2>Pessoas</h2>
      </header>

      {pessoas.length === 0 ? (
        <p className="estado-vazio">
          Nenhuma pessoa cadastrada ainda. Adicione o primeiro membro do grupo!
        </p>
      ) : (
        <ul className="lista-pessoa">
          {pessoas.map((pessoa) => (
            <li className="pessoa-item" key={pessoa.id}>
              <span className="pessoa-nome">{pessoa.nome}</span>
              <BotaoOikos variante="perigo" onClick={() => lidarExcluirPessoa(pessoa.id)}>
                Excluir
              </BotaoOikos>
            </li>
          ))}
        </ul>
      )}

      <div className="caixa-criar-pessoa">
        <InputOikos
          placeholder="Nome da nova pessoa"
          value={novoNome}
          onChange={(e) => setNovoNome(e.target.value)}
        />
        <BotaoOikos variante="primario" onClick={lidarCriarPessoa}>
          Adicionar Pessoa
        </BotaoOikos>
      </div>
    </section>
    </>
  );
}

export default GerenciadorPessoas;
