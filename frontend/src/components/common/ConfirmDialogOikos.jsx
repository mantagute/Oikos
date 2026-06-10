import { BotaoOikos } from './BotaoOikos';
import './ConfirmDialogOikos.css';

export function ConfirmDialogOikos({ mensagem, onConfirm, onCancel }) {
  return (
    <div className="confirm-overlay" onClick={onCancel}>
      <div className="confirm-dialog" onClick={e => e.stopPropagation()}>
        <p className="confirm-mensagem">{mensagem}</p>
        <div className="confirm-acoes">
          <BotaoOikos variante="secundario" onClick={onCancel}>
            Cancelar
          </BotaoOikos>
          <BotaoOikos variante="perigo" onClick={onConfirm}>
            Confirmar
          </BotaoOikos>
        </div>
      </div>
    </div>
  );
}
