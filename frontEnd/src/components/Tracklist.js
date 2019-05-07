import React, { Component } from "react";
import ReactTable from "react-table";
import "react-table/react-table.css";
import { SERVER_URL } from "../constants.js";
import "react-confirm-alert/src/react-confirm-alert.css";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Play from "./Play";

class Genres extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedTrack: null,
      player: "stopped",
      currentTime: null,
      duration: null,
      playlink: null
    };
  }

  componentWillMount() {
    this.fetchLists();
  }

  // Fetch all tracks
  fetchLists = () => {
    const token = sessionStorage.getItem("jwt");
    fetch(this.props.link, {
      headers: { Authorization: token }
    })
      .then(response => response.json())
      .then(responseData => {
        this.setState({
          genres: responseData._embedded.tracks
        });
      })
      .catch(err => console.error(err));
  };

  customFilter = (filter, row) => {
    const id = filter.pivotId || filter.id;
    if (row[id] !== null && typeof row[id] === "string") {
      return row[id] !== undefined
        ? String(row[id].toLowerCase()).includes(filter.value.toLowerCase())
        : true;
    }
  };

  renderEditable = cellInfo => {
    return (
      <div
        style={{ backgroundColor: "#fafafa" }}
        contentEditable
        suppressContentEditableWarning
        onBlur={e => {
          const data = [...this.state.genres];
          data[cellInfo.index][cellInfo.column.id] = e.target.innerHTML;
          this.setState({ genres: data });
        }}
        dangerouslySetInnerHTML={{
          __html: this.state.genres[cellInfo.index][cellInfo.column.id]
        }}
      />
    );
  };

  handleClose = (event, reason) => {
    this.setState({ open: false });
  };

  render() {
    const link = this.state.tracklink;

    const columns = [
      {
        Header: "Title",
        accessor: "title"
      },
      {
        Header: "Duration",
        accessor: "duration",
        filterable: false,
        width: 100
      },
      {
        Header: "Rank",
        accessor: "rank",
        filterable: false,
        width: 100
      },
      {
        accessor: "preview",
        sortable: false,
        filterable: false,
        width: 100,
        Cell: ({ value }) => (
          <Button
            size="small"
            variant="flat"
            variant="outlined"
            color="primary"
            onClick={() => {
              this.setState({ playlink: value });
            }}
          >
            Play
          </Button>
        )
      }
    ];
    var playlink = this.state.playlink;
    return (
      <div className="App">
        <Grid container>
          <Grid item />
        </Grid>
        <Play playlink={this.state.playlink} />
        <ReactTable
          data={this.state.genres}
          columns={columns}
          filterable={true}
          defaultFilterMethod={this.customFilter}
          pageSize={5}
          showPageSizeOptions={false}
          noDataText={"Plase Wait..."}
          showPaginationTop
          showPaginationBottom
        />
      </div>
    );
  }
}

export default Genres;
