FROM azul/zulu-openjdk:23-latest

# Create non-root user
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID
ARG MC_VERSION=1.21.3
ARG DEBIAN_FRONTEND=noninteractive

# Create the user
RUN apt-get update -y && \
    apt-get install -y \
        sudo \
        git \
        zip \
        unzip \
        curl \
        maven \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME \
    && echo "$USERNAME ALL=(root) NOPASSWD:ALL" > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME

# Switch to non-root user
USER $USERNAME
WORKDIR /workspace/server/build

# Build Spigot
RUN curl -s -S -L -o BuildTools.jar https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar && \
    java -jar BuildTools.jar --rev $MC_VERSION --remapped && \
    cp spigot-$MC_VERSION.jar ../

# Reset workdir to workspace
WORKDIR /workspace

CMD ["/bin/bash"]