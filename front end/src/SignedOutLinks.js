import React from "react";
import Reg from "./components/users/Reg";
import PlaylistsGest from "./components/PlaylistsGest";

const SignedOutLinks = () => {
  return (
    <div>
      <Reg />
      <PlaylistsGest />
    </div>
  );
};

export default SignedOutLinks;
