#
# CMakeLists.txt
#
# Copyright (C) 2009-12 by RStudio, Inc.
#
# This program is licensed to you under the terms of version 3 of the
# GNU Affero General Public License. This program is distributed WITHOUT
# ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
# MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
# AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
#
#

# set minimum version
cmake_minimum_required(VERSION 2.8)

# include globals. normally these are included at the root but we also
# include them here to support configuring at the src level (i.e. for
# development-configurations)
include("${CMAKE_CURRENT_SOURCE_DIR}/../CMakeGlobals.txt")

# don't add gwt for special 64-bit binary builds on windows (since
# we've already got it from the 32-bit build) or for development mode 
# (since we'll want to build it incrementally using superdevmode)
if ( (NOT RSTUDIO_SESSION_WIN64) AND (NOT RSTUDIO_DEVELOPMENT) )
   add_subdirectory(gwt)
endif()

add_subdirectory(cpp)

if(EXISTS "${CMAKE_CURRENT_SOURCE_DIR}/CMakeOverlay.txt")
   include(CMakeOverlay.txt)
endif()







   


