
export default function Status (props)
{
    return (
        <div>
            {props.value ?
                <img height="50" src="/imgs/status_up.png" /> :
                <img height="50" src="/imgs/status_down.png" />
            }
        </div>
    );
}