import {useState} from 'react';
import eventoService  from '../../services/eventoService';
import { InputOikos, BotaoOikos } from '../common';
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
            <ul className="lista-eventos">
                {eventos.map((evento) => (
                    <li key={evento.id} className="evento-item">
                        <span className="evento-nome">{evento.nome} ({evento.pontos} pts)</span>
                        <BotaoOikos variante="perigo" onClick={() => lidarExcluirEvento(evento.id)}>Excluir</BotaoOikos>
                    </li>
                ))}
            </ul>
            <div className='caixa-criar-evento'>
                <InputOikos 
                    placeholder="Nome do novo evento"
                    value={novoNome}
                    onChange={(e) => setNovoNome(e.target.value)}
                />
                <InputOikos 
                    type="number"
                    placeholder="Pontos"
                    value={pontos}
                    onChange={(e) => setPontos(e.target.value)}
                />
                <BotaoOikos variante="primario" onClick={lidarCriarEvento}>Adicionar Evento</BotaoOikos>
            </div> 
        </section>
    );
}

export default GerenciadorEventos;