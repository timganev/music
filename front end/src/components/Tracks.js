import React from "react";
import SkyLight from "react-skylight";
import Button from "@material-ui/core/Button";
import Tracklist from "./Tracklist";

class Tracks extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tracklink: this.props.value
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
    const link = this.state.tracklink;
    return (
      <div>
        <SkyLight hideOnOverlayClicked ref="addDialog">
          <h3>Tracks</h3>
          <Tracklist link={link} />

          <form>
            <Button
              variant="outlined"
              color="secondary"
              onClick={this.cancelSubmit}
            >
              Close
            </Button>
          </form>
        </SkyLight>
        <div>
          <Button
            className="pesho"
            variant="outlined"
            color="primary"
            style={{ margin: "2px" }}
            onClick={() => this.refs.addDialog.show()}
          >
            Play
          </Button>
        </div>
      </div>
    );
  }
}

export default Tracks;
