import React from "react";
import ReactDOM from "react-dom";

import "../scss/index.scss";
import Root from "./Root";

document.addEventListener("DOMContentLoaded", () => {

    const rootContainer = document.getElementById("root");

    if (rootContainer) {

        const SetControlActionURL = {
            urlControlActionGreeting: "/employees/",
            ActionControlDeleteEmployee: "/employees/",
        };

        ReactDOM.render(<Root SetControlActionURL={SetControlActionURL}/>, rootContainer);

    }
});
