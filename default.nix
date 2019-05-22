{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell rec {
  buildInputs = [
    pkgs.neo4j
  ];

  serverConfig = pkgs.writeText "neo4j.conf" ''
    dbms.directories.data=./cloggs
    dbms.directories.logs=./cloggs
  '';

  NEO4J_HOME = "${pkgs.neo4j}/share/neo4j";

  shellHook = ''
    #ln -s ${serverConfig} "$PWD"/.neo4j/neo4j.conf
    neodir="$PWD"/.neo4j
    mkdir -p "$neodir"
    mkdir -p "$neodir"/certificates
    mkdir -p "$neodir"/data
    mkdir -p "$neodir"/logs
    cat <<EOF > "$neodir"/neo4j.conf
dbms.directories.certificates=$neodir/certificates
dbms.directories.data=$neodir/data
dbms.directories.logs=$neodir/logs
dbms.security.auth_enabled=false
EOF
    export NEO4J_CONF="$neodir"
    '';
}
