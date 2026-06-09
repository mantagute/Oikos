import notificacaoService from "../../services/notificacaoService";

import "./GerenciadorNotificacoes.css"

function GerenciadorNotificacoes({grupoId, notificacoes, onAtualizarNotificacoes}) {

    const lidarMarcarComoConcluido = async (notificacaoId) => {
        try {
            await notificacaoService.marcarComoLida(grupoId, notificacaoId);
            onAtualizarNotificacoes();
        } catch (error) {
            console.error('Erro ao marcar notificacao como lida:', error);
        }
    }

    const lidarExcluirNotificacao = async (notificacaoId) => {
        try {
            await notificacaoService.excluirNotificacao(grupoId, notificacaoId);
            onAtualizarNotificacoes();
        } catch (error) {
            console.error('Erro ao excluir notificacao:', error);
        }
    }

    return (
        <section className='gerenciador-notificacoes'>
            <header>
                <h2>Notificações</h2>
            </header>
            <ul className='lista-notificacoes'>
                {notificacoes.map((notificacao) => (
                    <li className='notificacao' key={notificacao.id}>
                        <span>{notificacao.mensagem}</span>
                        <span className='notificacao-status'>{notificacao.lida ? 'Lida' : 'Não lida'}</span>
                        {!notificacao.lida && (
                            <button className='marcar-lida-button' onClick={() => lidarMarcarComoConcluido(notificacao.id)}>
                                Marcar como lida
                            </button>
                        )}
                        <button className='excluir-notificacao-button' onClick={() => lidarExcluirNotificacao(notificacao.id)}>
                            Excluir
                        </button>
                    </li>
                ))}
            </ul>
        </section>
    );
}

export default GerenciadorNotificacoes;