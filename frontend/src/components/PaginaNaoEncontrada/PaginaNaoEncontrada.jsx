import { useNavigate } from 'react-router-dom';
import { useTitle } from '../../hooks/useTitle';
import { FooterOikos, BotaoOikos } from '../common';
import './PaginaNaoEncontrada.css';

function PaginaNaoEncontrada() {
  useTitle('Página não encontrada');
  const navigate = useNavigate();

  return (
    <main className="pagina-404">
      <section className="pagina-404-conteudo">
        <h1 className="pagina-404-codigo">404</h1>
        <h2 className="pagina-404-titulo">Página não encontrada</h2>
        <p className="pagina-404-descricao">
          O caminho que você procurou não existe em nossa comunidade.
          Que tal voltar ao início?
        </p>
        <BotaoOikos variante="primario" onClick={() => navigate('/')}>
          Voltar ao Início
        </BotaoOikos>
      </section>
      <FooterOikos />
    </main>
  );
}

export default PaginaNaoEncontrada;
