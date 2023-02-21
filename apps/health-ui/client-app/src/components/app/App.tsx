import './App.css';
import { SystemMonitor } from '../system-monitor/system-monitor';
import React, { FC } from 'react';

export interface IAppProps {

};

export const App: FC<IAppProps> = (props: IAppProps) => {
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

