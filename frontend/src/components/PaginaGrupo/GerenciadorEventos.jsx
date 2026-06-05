import {useState} from 'react';
import eventoService  from '../../services/eventoService';

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
        <div className=''>
            <h3>Eventos</h3>
            <ul>
                {eventos.map((evento) => (
                    <li key={evento.id}>
                        {evento.nome}
                        <button onClick={() => lidarExcluirEvento(evento.id)}>Excluir</button>
                    </li>
                ))}
            </ul>
            <input 
                type="text"
                placeholder="Nome da nova evento"
                value={novoNome}
                onChange={(e) => setNovoNome(e.target.value)}
            />
            <input 
                type="number"
                placeholder="Pontos associados ao evento"
                value={pontos}
                onChange={(e) => setPontos(e.target.value)}
            />
            <button onClick={lidarCriarEvento}>Adicionar Evento</button> 
        </div>
    );
}

export default GerenciadorEventos;