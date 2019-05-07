import React, { Component } from "react";

class Weather extends Component {
  constructor(props) {
    super(props);
    this.state = {
      temp: 0,
      timezone: "",
      summary: "",
      lat: this.props.lat,
      lon: this.props.lon
    };
  }

  render() {
    var lon = this.state.lon;
    var lat = this.state.lat;
    const proxy = "https://cors-anywhere.herokuapp.com/";
    const api = `${proxy}https://api.darksky.net/forecast/8c41df8c79f567347763ce1cc29d89d6/${lat},${lon}`;
    fetch(api)
      .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data);

        const { temperature, summary } = data.currently;
        this.setState({
          temp: (temperature - 32) * (5 / 9),
          summary: summary,
          timezone: data.timezone
        });
      });

    return (
      <div>
        <p>
          {this.state.timezone} {Math.round(this.state.temp)}C{" "}
          {this.state.summary}
        </p>
      </div>
    );
  }
}

export default Weather;
