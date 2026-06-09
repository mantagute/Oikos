import { useParams, useNavigate } from 'react-router-dom';
import { useParoquia } from '../../hooks/useParoquia';
import DashboardGlobais from './DashboardGlobais';
import RankingGrupos from './RankingGrupos';
import PainelComunicacao from './PainelComunicacao';
import './PaginaParoquia.css';

function PaginaParoquia() {
    const { id } = useParams();
    const navigate = useNavigate();
    const hook = useParoquia(id);

    if (!hook.paroquia) {
        return <main className="pagina-paroquia">Carregando dados da paróquia...</main>;
    }

    return (
        <main className="pagina-paroquia">
        <header className="pagina-paroquia-header">
            <button className="pagina-paroquia-voltar" onClick={() => navigate('/')}>
            &lt;&lt; Voltar ao Início
            </button>
            <div className="pagina-paroquia-perfil">
            <h1 className="pagina-paroquia-titulo">{hook.paroquia.nome}</h1>
            <h2 className="pagina-paroquia-subtitulo">Dashboard da Comunidade</h2>
            </div>
        </header>

        <DashboardGlobais grupos={hook.gruposVinculados} />
        
        <RankingGrupos grupos={hook.gruposVinculados} />
        
        <PainelComunicacao hook={hook} />
        </main>
    );
}

export default PaginaParoquia;
