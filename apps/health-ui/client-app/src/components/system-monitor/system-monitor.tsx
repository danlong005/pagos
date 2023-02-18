import {useEffect, useState} from "react";
import Status from '../status/status';
import axios from 'axios';

import './system-monitor.css';

export default function SystemMonitor(props)
{
    const [status, setStatus] = useState(false);
    const [commit, setCommit] = useState("");

    useEffect(() => {
        axios.get(`${props.system.url}/v1/health`)
            .then((response) => {
                setStatus(response.data.status === "UP");
                window.data = response.data;
                setCommit(response.data.components.commit.details.Commit);
            }).catch((error) => {
               console.log(`Error: ${error}`);
            });
    }, [])

    return (
        <div className={"system-monitor-container"}>
            <div className={"system-monitor-status"}>
                <Status value={status} />
            </div>
            <div className={"system-monitor-details"}>
                <div className={"system-monitor-details-container"}>
                    <div className={"system-monitor-details-left"}>Name:</div>
                    <div className={"system-monitor-details-right"}>{props.system.name}</div>
                </div>
                <div className={"system-monitor-details-container"}>
                    <div className={"system-monitor-details-left"}>Url:</div>
                    <div className={"system-monitor-details-right"}>{props.system.url}</div>
                </div>
                <div className={"system-monitor-details-container"}>
                    <div className={"system-monitor-details-left"}>Commit:</div>
                    <div className={"system-monitor-details-right"}>{commit}</div>
                </div>
            </div>

        </div>
    );
}