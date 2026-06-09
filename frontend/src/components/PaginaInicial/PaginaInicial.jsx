import { useNavigate } from 'react-router-dom';
import { useTitle } from '../../hooks/useTitle';
import { FooterOikos } from '../common';
import './PaginaInicial.css';

function PaginaInicial() {
  useTitle();
  const navigate = useNavigate();

  return (
    <main className="pagina-inicial">
      <header className="pagina-inicial-header">
        <img src="/oikosLogo.svg" alt="Oikos Logo" className="logo-oikos" />
        <h2 className="pagina-inicial-tagline">
          Um lar digital para sua comunidade de fé
        </h2>
        <p className="pagina-inicial-descricao">
          Oikos transforma a prática espiritual individual em responsabilidade
          coletiva. Aqui, o crescimento de um edifica o todo.
        </p>
      </header>

      <section className="portal-container">
        <button
          className="portal-card"
          onClick={() => navigate('/acesso-grupos')}
        >
          <h3>Sou Membro</h3>
          <p>
            Acesse seus grupos, registre atividades e acompanhe o progresso da
            sua comunidade.
          </p>
        </button>

        <button
          className="portal-card paroquia-card"
          onClick={() => navigate('/acesso-paroquias')}
        >
          <h3>Sou Paróquia</h3>
          <p>
            Gerencie grupos, acompanhe métricas e fortaleça o vínculo com sua
            comunidade.
          </p>
        </button>
      </section>

      <FooterOikos />
    </main>
  );
}

export default PaginaInicial;
