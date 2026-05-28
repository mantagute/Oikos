import './App.css'

function App() {

  return (
    <>
      <body>
        <div id='titulo'>
          <h1>Oikos</h1>
          <h2>Seja bem vindo</h2>
        </div>
        <div id='grupos'>
          <div id="criar">
            <input 
              className='entrada'
              type="text"
              placeholder='+ Nome do novo grupo'
            />
            <input 
              className='entrada'
              type="text"
              placeholder='+ Senha do novo grupo'
            />
            <button 
              className='criarButton'
              onClick={() => {}}
            >
              + Criar Grupo
            </button>
          </div>
        </div>
      </body>
    </>
  )
}

export default App
