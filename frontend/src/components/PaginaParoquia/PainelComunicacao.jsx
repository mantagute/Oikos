import { InputOikos, BotaoOikos } from '../common';

function PainelComunicacao({ hook }) {
  return (
    <>
      <section className="painel-notificacoes">
        <h2>Comunicação e Notificações</h2>
        
        <InputOikos
          multiline
          rows={4}
          placeholder="Escreva a mensagem que deseja enviar..."
          value={hook.mensagemNotificacao}
          onChange={(e) => hook.setMensagemNotificacao(e.target.value)}
        />
        
        <footer className="painel-notificacoes-footer">
          <p className="dica-envio">
            {hook.gruposSelecionados.length === 0 
              ? "Enviando broadcast para TODOS os grupos." 
              : `Enviando para ${hook.gruposSelecionados.length} grupo(s) selecionado(s).`}
          </p>
          
          <BotaoOikos variante="primario" onClick={hook.lidarEnviarNotificacao}>
            Enviar Notificação
          </BotaoOikos>
        </footer>
      </section>

      <aside className="lista-grupos-vinculados">
        <h3>Filtrar destinatários da notificação</h3>
        {hook.gruposVinculados.length === 0 ? (
          <p className="dica-envio">Nenhum grupo vinculado.</p>
        ) : (
          <div className="grupos-grid">
            {hook.gruposVinculados.map((grupo) => (
              <label key={grupo.id} className="grupo-vinculado-item">
                <input 
                  type="checkbox"
                  className="grupo-checkbox"
                  checked={hook.gruposSelecionados.includes(grupo.id)}
                  onChange={() => hook.alternarSelecaoGrupo(grupo.id)}
                />
                <span className="grupo-nome">{grupo.nome}</span>
              </label>
            ))}
          </div>
        )}
      </aside>
    </>
  );
}

export default PainelComunicacao;
