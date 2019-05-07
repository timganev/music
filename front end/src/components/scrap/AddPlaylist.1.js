import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Genres from "../Genres";

class AddPlaylist extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      title: "",
      sameartist: false,
      topranks: false,
      data: [
        { duration: 500, genre: "Dance" },
        { duration: 500, genre: "Rock" }
      ]
    };
  }

  handleChange = event => {
    this.setState({ [event.target.name]: event.target.value });
  };

  // Save playlist and close modal form
  handleSubmit = event => {
    event.preventDefault();
    var newPlaylist = {
      username: this.state.username,
      title: this.state.title,
      sameartist: this.state.sameartist,
      topranks: this.state.topranks,
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
          <h3>New Playlist</h3>
          <Genres />
          <form>
            <TextField
              label="Username"
              placeholder="username"
              name="username"
              onChange={this.handleChange}
            />
            <br />
            <TextField
              label="Title"
              placeholder="title"
              name="title"
              onChange={this.handleChange}
            />
            <br />

            <Button
              variant="outlined"
              style={{ marginRight: 10 }}
              color="primary"
              onClick={this.handleSubmit}
            >
              Save
            </Button>
            <Button
              variant="outlined"
              color="secondary"
              onClick={this.cancelSubmit}
            >
              Cancel
            </Button>
          </form>
        </SkyLight>
        <div>
          <Button
            variant="raised"
            color="primary"
            style={{ margin: "10px" }}
            onClick={() => this.refs.addDialog.show()}
          >
            New Playlist
          </Button>
        </div>
      </div>
    );
  }
}

export default AddPlaylist;
