import { useNavigate } from 'react-router-dom';
import './PaginaInicial.css';

function PaginaInicial() {
  const navigate = useNavigate();

  return (
    <main className="pagina-inicial">
      <header className="pagina-inicial-header">
        <div>
          <h1>Oikos</h1>
          <h2>Seja bem vindo ao portal</h2>
        </div>
      </header>

      <section className="portal-container">
        <button 
          className="portal-card" 
          onClick={() => navigate('/acesso-grupos')}
        >
          <h3>Sou Membro</h3>
          <p>Acessar meus grupos e metas</p>
        </button>

        <button 
          className="portal-card paroquia-card" 
          onClick={() => navigate('/acesso-paroquias')}
        >
          <h3>Sou Paróquia</h3>
          <p>Acessar o painel de administração</p>
        </button>
      </section>
    </main>
  );
}

export default PaginaInicial;