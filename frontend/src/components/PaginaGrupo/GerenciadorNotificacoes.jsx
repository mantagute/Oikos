import { BsBell } from 'react-icons/bs';
import notificacaoService from '../../services/notificacaoService';
import { BotaoOikos } from '../common';
import './GerenciadorNotificacoes.css';

function GerenciadorNotificacoes({ grupoId, notificacoes, onAtualizarNotificacoes }) {
  const lidarMarcarComoConcluido = async (notificacaoId) => {
    try {
      await notificacaoService.marcarComoLida(grupoId, notificacaoId);
      onAtualizarNotificacoes();
    } catch (error) {
      console.error('Erro ao marcar notificacao como lida:', error);
    }
  };

  const lidarExcluirNotificacao = async (notificacaoId) => {
    if (!window.confirm('Tem certeza que deseja excluir esta notificação?')) return;
    try {
      await notificacaoService.excluirNotificacao(grupoId, notificacaoId);
      onAtualizarNotificacoes();
    } catch (error) {
      console.error('Erro ao excluir notificacao:', error);
    }
  };

  const naoLidas = notificacoes.filter((n) => !n.lida).length;

  return (
    <section className="gerenciador-notificacoes">
      <header className="secao-header">
        <BsBell className="secao-icone" />
        <h2>
          Notificações
          {naoLidas > 0 && <span className="badge-naoLidas">{naoLidas}</span>}
        </h2>
      </header>

      {notificacoes.length === 0 ? (
        <p className="estado-vazio">
          Nenhuma notificação recebida ainda. As novidades da sua paróquia aparecerão aqui.
        </p>
      ) : (
        <ul className="lista-notificacoes">
          {notificacoes.map((notificacao) => (
            <li
              className={`notificacao-item ${notificacao.lida ? 'lida' : 'nao-lida'}`}
              key={notificacao.id}
            >
              <p className="notificacao-mensagem">{notificacao.mensagem}</p>
              <div className="notificacao-acoes">
                {!notificacao.lida && (
                  <BotaoOikos
                    variante="primario"
                    onClick={() => lidarMarcarComoConcluido(notificacao.id)}
                  >
                    Marcar como lida
                  </BotaoOikos>
                )}
                <BotaoOikos variante="perigo" onClick={() => lidarExcluirNotificacao(notificacao.id)}>
                  Excluir
                </BotaoOikos>
              </div>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
}

export default GerenciadorNotificacoes;
