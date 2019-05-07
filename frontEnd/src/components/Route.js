import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import MapPoint from "./MapPoint";
import TextField from "@material-ui/core/TextField";
import PropTypes from "prop-types";

var APIkey = "AoaWJdtt7xo1wz2dvukLN0I3xmqTyBQqc3VrZJkgjE2ir1qOWrga8kQWuJHZRqkc";

class Route extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      temp: 0,
      desc: "",
      icon: "",
      startLat: 42.6,
      startLon: 23.3,
      endLat: 42.6,
      endLon: 23.3,
      startAddress: "BG/Sofia/Mladost/1729/Alexandar Malinov 31",
      stopAddress: "BG/Plovdiv/-/-/Bojur 1",
      distance: 0,
      duration: 0
    };
  }

  findPoint = async () => {
    let startAddress = this.state.startAddress;
    let stopAddress = this.state.stopAddress;

    await fetch(
      `http://dev.virtualearth.net/REST/v1/Locations/${startAddress}?key=${APIkey}`
    )
      .then(response => response.json())
      .then(responseData => {
        var anypoint = true;
        var geocodePoints =
          responseData.resourceSets[0].resources[0].geocodePoints;
        for (let i = 0; i < geocodePoints.length; i++) {
          if (geocodePoints[i].usageTypes[0] === "Route") {
            anypoint = false;
            this.setState({
              startLat: geocodePoints[i].coordinates[0],
              startLon: geocodePoints[i].coordinates[1]
            });
            console.log(geocodePoints[i]);
          }
        }

        if (anypoint) {
          this.setState({
            startLat: geocodePoints[0].coordinates[0],
            startLon: geocodePoints[0].coordinates[1]
          });
        }
      });

    await fetch(
      `http://dev.virtualearth.net/REST/v1/Locations/${stopAddress}?key=${APIkey}`
    )
      .then(response => response.json())
      .then(responseData => {
        let anypoint = true;
        let geocodePoints =
          responseData.resourceSets[0].resources[0].geocodePoints;
        for (let i = 0; i < geocodePoints.length; i++) {
          if (geocodePoints[i].usageTypes[0] === "Route") {
            anypoint = false;
            this.setState({
              endLat: geocodePoints[i].coordinates[0],
              endLon: geocodePoints[i].coordinates[1]
            });
            console.log(geocodePoints[i]);
          }
        }

        if (anypoint) {
          this.setState({
            endLat: geocodePoints[0].coordinates[0],
            endLon: geocodePoints[0].coordinates[1]
          });
        }
      });
  };

  calculate = () => {
    let startLat = this.state.startLat;
    if (startLat === undefined) {
      startLat = 0;
    }
    let startLon = this.state.startLon;
    let endLat = this.state.endLat;
    let endLon = this.state.endLon;

    fetch(
      `https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?origins=${startLat},${startLon}&destinations=${endLat},${endLon}&travelMode=driving&key=${APIkey}`
    )
      .then(response => response.json())
      .then(responseData => {
        this.setState({
          duration:
            responseData.resourceSets[0].resources[0].results[0].travelDuration,
          distance:
            responseData.resourceSets[0].resources[0].results[0].travelDistance
        });
        var duration = this.state.duration;
        this.props.updateDuration(Math.round(duration));
      });
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  // Save playlist and close modal form
  handleSubmit = event => {
    event.preventDefault();
    var newPlaylist = {
      username: this.state.username,
      title: this.state.title,
      data: this.state.data
    };

    this.props.addPlaylist(newPlaylist);
    this.refs.addDialog.hide();
  };

  cancelSubmit = event => {
    event.preventDefault();
    this.refs.addDialog.hide();
  };

  render() {
    return (
      <div>
        <SkyLight hideOnOverlayClicked ref="addDialog">
          {/* <MapPoint updateDuration={this.updateDuration} /> */}
          <div>
            Geo points modul
            <div>
              <div id="mapid" />
              <br />
              <TextField
                type="text"
                name="startAddress"
                label="start address"
                value={this.state.startAddress}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
              />

              <TextField
                type="text"
                name="stopAddress"
                label="stop address"
                value={this.state.stopAddress}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
              />
              <br />
              <Button
                variant="outlined"
                color="primary"
                onClick={this.findPoint}
                margin="normal"
              >
                find geo Points
              </Button>
              <br />
              <TextField
                type="number"
                min="0"
                name="startLat"
                label="start lat"
                value={this.state.startLat}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
              />

              <TextField
                type="number"
                min="0"
                name="startLon"
                label="start lon"
                value={this.state.startLon}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
              />

              <br />

              <TextField
                type="number"
                min="0"
                name="endLat"
                label="end lat"
                value={this.state.endLat}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
              />
              <TextField
                type="number"
                min="0"
                name="endLon"
                label="end lon"
                value={this.state.endLon}
                onChange={this.handleChange}
                variant="outlined"
                margin="normal"
                defaultValue="number"
              />
              <br />
              <Button
                variant="outlined"
                color="primary"
                onClick={this.calculate}
              >
                Calculate time
              </Button>
              <Button
                variant="outlined"
                color="secondary"
                onClick={this.cancelSubmit}
              >
                Close
              </Button>

              <p>Duration: {Math.round(this.state.duration)} min</p>

              <br />

              <br />
            </div>
          </div>
        </SkyLight>
        <div>
          <Button
            size="small"
            variant="flat"
            color="primary"
            variant="outlined"
            onClick={() => this.refs.addDialog.show()}
          >
            Route
          </Button>
        </div>
      </div>
    );
  }
}

export default Route;
