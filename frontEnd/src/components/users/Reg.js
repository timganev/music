import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
////

import Snackbar from "@material-ui/core/Snackbar";
import MapPoint from "../MapPoint";
import Playlists from "../Playlists";
import Users from "./Users";
import { SERVER_URL } from "../../constants.js";

class Log extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      isAuthenticated: false,
      open: false
    };
  }
  register = () => {
    const user = {
      username: this.state.username,
      password: this.state.password
    };
    fetch(SERVER_URL + "signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(user)
    })
      .then(res => {
        if (res.status == 200) {
          console.log(res.status + "successful registration!!!");
        } else {
          console.log(res.status + " registration failed!!!");
        }

        console.log(res.status);
      })
      .catch(err => console.error(err));
    this.refs.addDialog.hide();
  };

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  handleClose = event => {
    this.setState({ open: false });
  };

  ///////////////////////
  // handleChange = event => {
  //   this.setState({ [event.target.name]: event.target.value });
  // };

  // // Save playlist and close modal form
  // handleSubmit = event => {
  //   event.preventDefault();
  //   var newPlaylist = {
  //     username: this.state.username,
  //     title: this.state.title,
  //     duration: this.state.duration
  //   };
  //   this.props.addPlaylist(newPlaylist);
  //   this.refs.addDialog.hide();
  // };

  cancelSubmit = event => {
    event.preventDefault();
    this.refs.addDialog.hide();
  };
  //////////////////////////////////
  render() {
    if (this.state.isAuthenticated === true) {
      return (
        <div>
          <MapPoint />
          <Playlists />;
          <Users />
        </div>
      );
    } else {
      return (
        <div>
          <SkyLight hideOnOverlayClicked ref="addDialog">
            <h3>REGISTER</h3>
            <br />
            <br />
            <form>
              <TextField
                label="username"
                placeholder="username"
                name="username"
                onChange={this.handleChange}
              />
              <br />
              <TextField
                label="password"
                type="password"
                placeholder="password"
                name="password"
                onChange={this.handleChange}
              />

              <br />
              <br />

              <Button
                variant="outlined"
                style={{ marginRight: 10 }}
                color="primary"
                onClick={this.register}
              >
                REGISTER
              </Button>
              <Button
                variant="outlined"
                color="secondary"
                onClick={this.cancelSubmit}
              >
                Cancel
              </Button>
              <Snackbar
                open={this.state.open}
                onClose={this.handleClose}
                autoHideDuration={1500}
                message="Check your username and password"
              />
            </form>
          </SkyLight>
          <div>
            <Button
              variant="raised"
              color="primary"
              style={{ margin: "10px" }}
              onClick={() => this.refs.addDialog.show()}
            >
              REGISTER
            </Button>
          </div>
        </div>
      );
    }
  }
}
export default Log;
