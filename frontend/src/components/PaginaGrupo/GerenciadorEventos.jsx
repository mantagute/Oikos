import {useState} from 'react';
import eventoService  from '../../services/eventoService';
import './GerenciadorEventos.css';

function GerenciadorEventos({grupoId, eventos, onAtualizarEventos }) {
    const [novoNome, setNovoNome] = useState('');
    const [pontos, setPontos] = useState(0);

    const lidarCriarEvento = async () => {
        if (!novoNome.trim()) return alert( 'Digite um nome para o novo evento');

        try {
            await eventoService.criarEvento(grupoId, novoNome, pontos);
            setNovoNome('');
            setPontos(0);
            onAtualizarEventos();
        }
        catch (error) {
            console.error('Erro ao criar evento:', error);
        }
    }

    const lidarExcluirEvento = async (eventoId) => {
        try {
            await eventoService.excluirEvento(grupoId, eventoId);
            onAtualizarEventos();
        }
        catch (error) {
            console.error('Erro ao excluir evento:', error);
        }
    }

    return (
        <section className='gerenciador-eventos'>
            <header>
                <h2>Eventos</h2>
            </header>
            <ul>
                {eventos.map((evento) => (
                    <li key={evento.id}>
                        {evento.nome}
                        <button className='excluir-button' onClick={() => lidarExcluirEvento(evento.id)}>Excluir</button>
                    </li>
                ))}
            </ul>
            <div className='caixa-criar-evento'>
                <input 
                    type="text"
                    placeholder="Nome do novo evento"
                    value={novoNome}
                    onChange={(e) => setNovoNome(e.target.value)}
                    className='input-novo-evento'
                />
                <input 
                    type="number"
                    placeholder="Pontos"
                    value={pontos}
                    onChange={(e) => setPontos(e.target.value)}
                    className='input-novo-evento-pontos'
                />
                <button className="criar-button" onClick={lidarCriarEvento}>Adicionar Evento</button>
            </div> 
        </section>
    );
}

export default GerenciadorEventos;