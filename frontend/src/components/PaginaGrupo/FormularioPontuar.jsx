import { BotaoOikos, SelectOikos } from '../common';
import './FormularioPontuar.css';

function FormularioPontuar({ pessoas, eventos, pessoaSelecionada, eventoSelecionado, onChangePessoa, onChangeEvento, onPontuar }) {
  return (
    <section className="pagina-grupo-pontuar">
      <header className="pontuar-header">
        <h2>Registrar Atividade</h2>
        <p className="pontuar-subtitulo">Atribua pontos a um membro pela participação</p>
      </header>
      <SelectOikos
        placeholder="Selecione uma pessoa"
        value={pessoaSelecionada || ''}
        onChange={(e) => onChangePessoa(e.target.value)}
      >
        {pessoas.map((pessoa) => (
          <option key={pessoa.id} value={pessoa.id}>
            {pessoa.nome}
          </option>
        ))}
      </SelectOikos>

      <SelectOikos
        placeholder="Selecione um evento"
        value={eventoSelecionado || ''}
        onChange={(e) => onChangeEvento(e.target.value)}
      >
        {eventos.map((evento) => (
          <option key={evento.id} value={evento.id}>
            {evento.nome} ({evento.pontos} pts)
          </option>
        ))}
      </SelectOikos>

      <BotaoOikos variante="primario" onClick={onPontuar}>
        Pontuar
      </BotaoOikos>
    </section>
  );
}

export default FormularioPontuar;
