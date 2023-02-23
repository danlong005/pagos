import './App.css';
import React, { FC } from 'react';

export interface IAppProps {

};

export const App: FC<IAppProps> = (props: IAppProps) => {
    return (
        <div className="App">
            <h2>Payments</h2>
        </div>
    );
}

