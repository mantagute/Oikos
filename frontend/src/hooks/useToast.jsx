import { useState, useCallback } from 'react';

function useToast() {
    const [toast, setToast] = useState(null); // { mensagem, variante: 'sucesso' | 'erro' }

    const mostrarToast = useCallback((mensagem, variante = 'erro') => {
        setToast({ mensagem, variante });
        setTimeout(() => setToast(null), 3500);
    }, []);

    const fecharToast = useCallback(() => setToast(null), []);

    return { toast, mostrarToast, fecharToast };
}

export default useToast;