import './App.css';
import SystemMonitor from './components/system-monitor/system-monitor';

function App() {
    let systems = [
        {
            key: 1,
            name: "Identities",
            url: `http://localhost:8080`
        },
        {
            key: 2,
            name: "Vault",
            url: `http://localhost:8081`
        },
        {
            key: 3,
            name: "Transactor",
            url: `http://localhost:8082`
        },
        {
            key: 4,
            name: "Orchestrator",
            url: `http://localhost:8083`
        }
    ];

    const systemMonitors = systems.map((system) => {
        return <SystemMonitor key={system.key} system={system} />
    });

    return (
        <div className="App">
            {systemMonitors}
        </div>
    );
}

export default App;
