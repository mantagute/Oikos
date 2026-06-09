import { useEffect } from 'react';

export function useTitle(titulo) {
  useEffect(() => {
    document.title = titulo ? `${titulo} · Oikos` : 'Oikos — Where Faith Finds Home';
  }, [titulo]);
}
