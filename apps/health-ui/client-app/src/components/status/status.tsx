import React, { FC } from 'react';

export interface IStatusProps {
    value: boolean;
};

export const Status: FC<IStatusProps> = (props: IStatusProps) =>
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