import { BrowserRouter, Routes, Route } from 'react-router-dom';
import PaginaInicial from './components/PaginaInicial/PaginaInicial';
import PaginaGrupo from './components/PaginaGrupo/PaginaGrupo';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<PaginaInicial />} />
        <Route path="/grupo/:id" element={<PaginaGrupo />} />
        <Route path="/paroquia/:id" element={<PaginaParoquia />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
