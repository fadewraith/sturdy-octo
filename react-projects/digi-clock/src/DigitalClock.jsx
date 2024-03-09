import { useEffect } from "react";
import { useState } from "react";

const DigitalClock = () => {

    const [ time, setTime ] = useState("");

    useEffect(() => {
        const interval = setInterval(() => {
            const date = new Date();
            let hours = date.getHours();
            let minutes = String(date.getMinutes()).padStart(2, "0");
            let seconds = String(date.getSeconds()).padStart(2, "0");
    
            hours = hours % 24;
            hours = hours ? hours : 12;
            hours = String(hours).padStart(2, "0");
            
            setTime(`${hours}:${minutes}:${seconds}`)
        }, 1000)


        return () => clearInterval(interval);

    }, [])

    
    return (
        <div className="bg-slate-900 p-10 rounded-xl">
            <p className="text-white text-9xl">{time}</p>
        </div>
    )
}

export default DigitalClock;