import { useState, useCallback } from 'react';
import { ConfirmDialogOikos } from '../components/common';

export function useConfirm() {
  const [state, setState] = useState(null);

  const confirmar = useCallback((mensagem) => {
    return new Promise((resolve) => {
      setState({
        mensagem,
        onConfirm: () => { setState(null); resolve(true); },
        onCancel: () => { setState(null); resolve(false); },
      });
    });
  }, []);

  const ConfirmDialog = state ? (
    <ConfirmDialogOikos
      mensagem={state.mensagem}
      onConfirm={state.onConfirm}
      onCancel={state.onCancel}
    />
  ) : null;

  return { confirmar, ConfirmDialog };
}
