import { IoArrowBack } from 'react-icons/io5';
import { useNavigate } from 'react-router-dom';
import './HeaderOikos.css';

export function HeaderOikos({
  mostrarVoltar = false,
  mostrarLogo = false,
  titulo,
  subtitulo,
  rotaVoltar = '/'
}) {
  const navigate = useNavigate();

  return (
    <header className={`header-oikos ${titulo ? 'header-oikos-com-titulo' : ''}`}>
      {mostrarVoltar && (
        <button className="header-oikos-voltar" onClick={() => navigate(rotaVoltar)}>
          <IoArrowBack size={14} />
          <span>Voltar</span>
        </button>
      )}
      {mostrarLogo && (
        <img src="/oikosLogo.svg" alt="Oikos" className="header-oikos-logo" />
      )}
      {titulo && (
        <div className="header-oikos-titulos">
          <h1 className="header-oikos-titulo">{titulo}</h1>
          {subtitulo && <p className="header-oikos-subtitulo">{subtitulo}</p>}
        </div>
      )}
    </header>
  );
}
