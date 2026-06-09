import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PaginaInicial from './components/PaginaInicial/PaginaInicial';
import PaginaAcessoGrupos from './components/PaginaInicial/PaginaAcessoGrupos';
import PaginaAcessoParoquias from './components/PaginaInicial/PaginaAcessoParoquias';
import PaginaGrupo from './components/PaginaGrupo/PaginaGrupo';
import PaginaParoquia from './components/PaginaParoquia/PaginaParoquia';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PaginaInicial />} />
        <Route path="/acesso-grupos" element={<PaginaAcessoGrupos />} />
        <Route path="/acesso-paroquias" element={<PaginaAcessoParoquias />} />
        <Route path="/grupo/:id" element={<PaginaGrupo />} />
        <Route path="/paroquia/:id" element={<PaginaParoquia />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
