import { useState } from 'react';
import { BsCalendarEvent } from 'react-icons/bs';
import eventoService from '../../services/eventoService';
import { InputOikos, BotaoOikos } from '../common';
import { useConfirm } from '../../hooks/useConfirm';
import './GerenciadorEventos.css';

function GerenciadorEventos({ grupoId, eventos, onAtualizarEventos, onErro }) {
  const { confirmar, ConfirmDialog } = useConfirm();
  const [novoNome, setNovoNome] = useState('');
  const [pontos, setPontos] = useState(0);

  const lidarCriarEvento = async () => {
    if (!novoNome.trim()) return onErro('Digite um nome para o novo evento.', 'erro');

    try {
      await eventoService.criarEvento(grupoId, novoNome, pontos);
      setNovoNome('');
      setPontos(0);
      onAtualizarEventos();
    } catch (error) {
      console.error('Erro ao criar evento:', error);
    }
  };

  const lidarExcluirEvento = async (eventoId) => {
    const confirmado = await confirmar('Tem certeza que deseja excluir este evento?');
    if (!confirmado) return;
    try {
      await eventoService.excluirEvento(grupoId, eventoId);
      onAtualizarEventos();
    } catch (error) {
      console.error('Erro ao excluir evento:', error);
    }
  };

  return (
    <>
      {ConfirmDialog}
      <section className="gerenciador-eventos">
      <header className="secao-header">
        <BsCalendarEvent className="secao-icone" />
        <h2>Eventos</h2>
      </header>

      {eventos.length === 0 ? (
        <p className="estado-vazio">
          Nenhum evento criado ainda. Crie atividades para fortalecer sua comunidade!
        </p>
      ) : (
        <ul className="lista-eventos">
          {eventos.map((evento) => (
            <li key={evento.id} className="evento-item">
              <span className="evento-nome">
                {evento.nome} ({evento.pontos} pts)
              </span>
              <BotaoOikos variante="perigo" onClick={() => lidarExcluirEvento(evento.id)}>
                Excluir
              </BotaoOikos>
            </li>
          ))}
        </ul>
      )}

      <div className="caixa-criar-evento">
        <InputOikos
          placeholder="Nome do novo evento"
          value={novoNome}
          onChange={(e) => setNovoNome(e.target.value)}
        />
        <InputOikos
          type="number"
          placeholder="Pontos"
          value={pontos}
          onChange={(e) => setPontos(e.target.value)}
        />
        <BotaoOikos variante="primario" onClick={lidarCriarEvento}>
          Adicionar Evento
        </BotaoOikos>
      </div>
    </section>
    </>
  );
}

export default GerenciadorEventos;
